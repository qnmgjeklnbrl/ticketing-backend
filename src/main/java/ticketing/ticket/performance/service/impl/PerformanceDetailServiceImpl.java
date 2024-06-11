package ticketing.ticket.performance.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ticketing.ticket.exception.PageantionException;
import ticketing.ticket.performance.domain.dto.IdxInfoDto;
import ticketing.ticket.performance.domain.dto.PerfSearchDto;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;
import ticketing.ticket.performance.domain.entity.PerformanceDetail;
import ticketing.ticket.performance.repository.PerformanceDetailRepository;
import ticketing.ticket.performance.repository.PerformanceRepository;
import ticketing.ticket.performance.service.PerformanceDetailService;
import ticketing.ticket.reservation.domain.entity.SeatReservation;
import ticketing.ticket.reservation.repository.SeatReservationRepository;
import ticketing.ticket.seat.domain.entity.Seat;
import ticketing.ticket.seat.repository.SeatRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceDetailServiceImpl implements PerformanceDetailService{
    private final PerformanceDetailRepository performanceDetailRepository;
    private final PerformanceRepository performanceRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final SeatRepository seatRepository;
   
    // 공연 디테일 저장
    @Override
    public void setPerformanceDetail(PerformanceDetailDto performanceDetailDto) {
        PerformanceDetail performanceDetail = new PerformanceDetail();
        performanceDetail.setArtist(performanceDetailDto.getArtist());
        performanceDetail.setStartTime(performanceDetailDto.getStartTime());
        performanceDetail.setEndTime(performanceDetailDto.getEndTime());
        performanceDetail.setPrice(performanceDetailDto.getPrice());
        performanceDetail.setPerformance(performanceRepository.findById(performanceDetailDto.getPerformanceId()));
        
        performanceDetailRepository.save(performanceDetail);
        // SeatReservation 벌크 인서트
        List<Seat> sList = seatRepository.findAll();
        List<SeatReservation> srList = new ArrayList<>();
        sList.forEach(s->{
            SeatReservation  seatReservation = new SeatReservation();
            seatReservation.setPerformanceDetail(performanceDetail);
            seatReservation.setSeat(s);
            srList.add(seatReservation);
        });
        
        seatReservationRepository.bulkInsert(srList);


        
    }
    // 공연 디테일 단건 조회
    @Override
    public PerformanceDetailDto getPerformanceDetail(Long performanceDetailId) {
        PerformanceDetail performanceDetail = performanceDetailRepository.findById(performanceDetailId);
        return performanceDetail.toDto();
    }
    // 공연 디테일 모두 조회
    @Override
    public List<PerformanceDetailDto> getAllPerformanceDetail() {
        List<PerformanceDetail> performanceDetailList = performanceDetailRepository.findAll();
        List<PerformanceDetailDto> dtoList = performanceDetailList.stream()
            .map(PerformanceDetail::toDto)
            .collect(Collectors.toList());
        return dtoList;
    }
    @Override
    public List<PerformanceDetailDto> getPerformanceDetailByPerformanceId(PerfSearchDto perfSearchDto) throws PageantionException  {
        if (perfSearchDto.getIndex() != null) {
            if (Long.valueOf(perfSearchDto.getIndex()).equals(performanceDetailRepository.findMinIdByPerformanceId(perfSearchDto.getPerfId()))) { // 마지막 페이지인 경우
                throw new PageantionException("마지막 페이지 입니다.");
             } else if(Long.valueOf(perfSearchDto.getIndex()).equals(performanceDetailRepository.findMaxIdByPerformanceId(perfSearchDto.getPerfId()))) { // 첫 페이지인 경우
                 throw new PageantionException("첫 페이지 입니다.");
             }
        }
        
       List<PerformanceDetail> pdList = performanceDetailRepository.findByPerformanceId(perfSearchDto);
       List<PerformanceDetailDto> dtoList = new ArrayList<>();
       pdList.stream()
       .map(PerformanceDetail::toDto)
       .forEach(dtoList::add);
       return dtoList;
    }

    @Override
    public void deletePerformanceDetail(Long performanceDetailId) {
        performanceDetailRepository.deleteById(performanceDetailId);
    }
    @Override
    public IdxInfoDto getIdxInfoByPerformanceId (Long performanceId) {
        Long maxIdx = performanceDetailRepository.findMaxIdByPerformanceId(performanceId);
        Long minIdx = performanceDetailRepository.findMinIdByPerformanceId(performanceId);
        return IdxInfoDto.builder().maxIdx(maxIdx).minIdx(minIdx).build();
    }
    @Override
    public IdxInfoDto getIdxInfo() {
        // TODO Auto-generated method stub
       Long maxIdx = performanceDetailRepository.findMaxId();
       Long minIdx = performanceDetailRepository.findMinId();
       return IdxInfoDto.builder().maxIdx(maxIdx).minIdx(minIdx).build();
    }
    
}
