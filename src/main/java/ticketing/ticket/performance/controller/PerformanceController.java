package ticketing.ticket.performance.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticketing.ticket.exception.PageantionException;
import ticketing.ticket.performance.domain.dto.IdxInfoDto;
import ticketing.ticket.performance.domain.dto.PerfSearchDto;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;
import ticketing.ticket.performance.domain.dto.PerformanceDto;
import ticketing.ticket.performance.service.PerformanceDetailService;
import ticketing.ticket.performance.service.PerformanceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
public class PerformanceController {
    private final PerformanceService performanceService;
    private final PerformanceDetailService performanceDetailService;

    @Autowired
    public PerformanceController(PerformanceService performanceService, PerformanceDetailService performanceDetailService){
        this.performanceService = performanceService;
        this.performanceDetailService = performanceDetailService;
    }
    ///////////////////////////////////// 공연 카테고리 ////////////////////////////////////
    // 공연 카테고리 저장
    
    @PostMapping("/perform/save")
    public ResponseEntity<Void> setPerformance(@RequestBody PerformanceDto performanceDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse(null);
        System.out.println("Current authority: " + authority);
        
        performanceService.setPerformance(performanceDto);
        return ResponseEntity.ok().build();
    }


    // 단일 공연 카테고리 조회
    @GetMapping("/perform/get-performance/{performanceId}")
    public ResponseEntity<PerformanceDto> getPerformance(@PathVariable Long performanceId){
        return ResponseEntity.ok(performanceService.getPerformance(performanceId));
    }
    // 모든 공연 카테고리 조회
    @GetMapping("/perform/all")
    public ResponseEntity<List<PerformanceDto>> getAllPerformance() {
       List<PerformanceDto> dtoList = performanceService.getAllPerformance();
       
        return ResponseEntity.ok(dtoList);

    }
    // 공연 카테고리 삭제
    @DeleteMapping("/delete-performance/{performanceId}")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long performanceId){
        performanceService.deletePerformance(performanceId);
        return ResponseEntity.ok().build();
    }

    ///////////////////////////////////공연 디테일 /////////////////////////////////////


    // 공연 디테일 저장
    @Secured("ROLE_ADMIN")
    @PostMapping("/perform-detail/save")
    public ResponseEntity<Void> setPerformanceDetail(@RequestBody PerformanceDetailDto performanceDetailDto){
        performanceDetailService.setPerformanceDetail(performanceDetailDto);
        return ResponseEntity.ok().build();
    }

    // 공연 디테일 단건조회
    @GetMapping("/perform-detail/get-performancedetail/{performanceDetailId}")
    public ResponseEntity<PerformanceDetailDto> getPerformanceDetail(@PathVariable Long performanceDetailId){
        return ResponseEntity.ok(performanceDetailService.getPerformanceDetail(performanceDetailId));
    }
    
    // 공연 디테일 모두 조회
    @GetMapping("/perform-detail/get-allperformancedetail")
    public ResponseEntity<List<PerformanceDetailDto>> getAllPerformanceDetail(){
        return ResponseEntity.ok(performanceDetailService.getAllPerformanceDetail());
    }
    // 공연 카테고리로 디테일 조회
    @PostMapping("/perform-detail/all")
    public ResponseEntity<List<PerformanceDetailDto>> getPerformanceDetailByPerformanceId (@RequestBody PerfSearchDto perfSearchDto) throws PageantionException {
        return ResponseEntity.ok(performanceDetailService.getPerformanceDetailByPerformanceId(perfSearchDto));
    }

    
    // 공연 디테일 삭제
    @DeleteMapping("/delete-performancedetailId/{performanceDetailId}")
    public ResponseEntity<Void> deletePerformanceDetail(@PathVariable Long PerformanceDetailId){
        performanceDetailService.deletePerformanceDetail(PerformanceDetailId);
        return ResponseEntity.ok().build();
    }
    // 
    @GetMapping("/perform-detail/get-idxinfo/{performanceId}")
    public ResponseEntity<IdxInfoDto> getIdxInfo(@PathVariable Long performanceId){

      
        return ResponseEntity.ok(performanceDetailService.getIdxInfoByPerformanceId(performanceId));
       
    }
    @GetMapping("/perform-detail/get-idxinfo/null")
    public ResponseEntity<IdxInfoDto> getIdxInfo(){
        return ResponseEntity.ok(performanceDetailService.getIdxInfo());
    }

    
}
