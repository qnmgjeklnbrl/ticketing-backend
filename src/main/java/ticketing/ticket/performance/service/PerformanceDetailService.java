package ticketing.ticket.performance.service;

import ticketing.ticket.performance.domain.dto.IdxInfoDto;
import ticketing.ticket.performance.domain.dto.PerfSearchDto;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;
import java.util.List;

public interface PerformanceDetailService {
    // 공연 디테일 저장
    void setPerformanceDetail(PerformanceDetailDto performanceDetailDto);

    // 공연 디테일 조회
    PerformanceDetailDto getPerformanceDetail(Long performanceDetailId);

    // 공연 디테일 모두 조회
    List<PerformanceDetailDto> getAllPerformanceDetail();

    //공연 디테일 삭제
    void deletePerformanceDetail(Long performanceDetailId);

    // 카테고리별 조회
    List<PerformanceDetailDto> getPerformanceDetailByPerformanceId(PerfSearchDto perfSearchDto);

    IdxInfoDto getIdxInfoByPerformanceId(Long performanceId);

    IdxInfoDto getIdxInfo();
}
