package br.dev.ltres.poc.integrateams.infrastructure.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.dev.ltres.poc.integrateams.application.usecases.AgendaEventoParaMSGraph;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AgendaEventoSincronizaScheduler {
    private final AgendaEventoParaMSGraph agendaEventoParaMSGraph;

    private int quantidade_falhas_para_suspensao = 5;
    private int tempo_para_reativao_apos_suspensao = 10; // em minutos
    private boolean ativo = true;
    private int errosConsecutivos = 0;
    private LocalDateTime suspensoEm;

    public AgendaEventoSincronizaScheduler(AgendaEventoParaMSGraph agendaEventoParaMSGraph,
            @Value("${agenda.sincronizacao.quantidade-falhas-para-suspensao}") int quantidade_falhas_para_suspensao,
            @Value("${agenda.sincronizacao.tempo-para-reativao-apos-suspensao}") int tempo_para_reativao_apos_suspensao) {
        this.agendaEventoParaMSGraph = agendaEventoParaMSGraph;
        this.quantidade_falhas_para_suspensao = quantidade_falhas_para_suspensao;
        this.tempo_para_reativao_apos_suspensao = tempo_para_reativao_apos_suspensao;
    }

    @Scheduled(fixedRateString = "${agenda.sincronizacao.tempo-fixo}")
    public void sincronizaEventos() {
        if (!ativo) {
            if (!VerificaSeReativa())
                return;
        }

        try {
            var qtdeSincronizada = agendaEventoParaMSGraph.executa();
            if (qtdeSincronizada > 0)
                log.info(String.format("Sincronizado %d evento(s).", qtdeSincronizada));
            else
                log.trace("Nenhum evento para sincronizar.");

            errosConsecutivos = 0;
        } catch (Exception e) {
            log.error("Erro ao sincronizar eventos.", e);
            errosConsecutivos++;
            VerificaSeSuspende();
        }
    }

    private void VerificaSeSuspende() {
        if (errosConsecutivos >= quantidade_falhas_para_suspensao) {
            suspensoEm = LocalDateTime.now();
            ativo = false;
            log.error("Scheduler suspenso após {} erros consecutivos", errosConsecutivos);
        }
    }

    private boolean VerificaSeReativa() {
        var tempoSuspenso = Duration.between(suspensoEm, LocalDateTime.now());
        if (tempoSuspenso.toMinutes() >= tempo_para_reativao_apos_suspensao) {
            ativo = true;
            suspensoEm = null;
            errosConsecutivos = 0;

            log.warn("Scheduler reativado automaticamente após {} minutos de suspensão",
                    tempo_para_reativao_apos_suspensao);
            return true;
        }
        return false;
    }

}
