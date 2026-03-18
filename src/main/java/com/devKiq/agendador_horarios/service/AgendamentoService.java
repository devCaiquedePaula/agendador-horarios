package com.devKiq.agendador_horarios.service;

import com.devKiq.agendador_horarios.infrastructure.entity.AgendamentoEntity;
import com.devKiq.agendador_horarios.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor // Injeção de dependência automática para os campos finais
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;
    
    public AgendamentoEntity salvarAgendamento(AgendamentoEntity agendamento){ 
        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);
        AgendamentoEntity agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(
                agendamento.getServico(), horaAgendamento, horaFim
        );
        if (Objects.nonNull(agendados)){
            throw new RuntimeException("Já existe um agendamento para este serviço nesse horário.");
        } 
        return agendamentoRepository.save(agendamento);
    }
    
    public void delatarAgendamento(LocalDateTime dataHoraAgendamento, String cliente){
        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }
    
    public List<AgendamentoEntity> buscarAgendamentosDia(LocalDate data){
        LocalDateTime primeiraHoraDoDia = data.atStartOfDay();
        LocalDateTime horaFinalDoDia = data.atTime(23, 59, 59);
        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDoDia, horaFinalDoDia);
    }
    
    public AgendamentoEntity alterarAgendamento(AgendamentoEntity agendamento, String cliente, LocalDateTime dataHoraAgendamento){
        AgendamentoEntity agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
        
        if (Objects.isNull(agenda)){
            throw new RuntimeException("Agendamento não encontrado para o cliente e horário especificados.");
        }
        agendamento.setId(agenda.getId());
        return agendamentoRepository.save(agendamento);
    }
}
