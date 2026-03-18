package com.devKiq.agendador_horarios.infrastructure.repository;

import com.devKiq.agendador_horarios.infrastructure.entity.AgendamentoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
    AgendamentoEntity findByServicoAndDataHoraAgendamentoBetween(
            String servico, 
            LocalDateTime dataHoraInicio, 
            LocalDateTime dataHoraFim);
    
    @Transactional // Transactional é importante, pois a operação de exclusão deve ser realizada numa transação para garantir a integridade dos dados
    void deleteByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento, 
            String cliente);
    
    List<AgendamentoEntity> findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);
    
    AgendamentoEntity findByDataHoraAgendamentoAndCliente(
            LocalDateTime dataHoraAgendamento,
            String cliente);
}
