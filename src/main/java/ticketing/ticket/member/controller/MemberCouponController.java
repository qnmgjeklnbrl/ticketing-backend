package ticketing.ticket.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticketing.ticket.member.domain.dto.MemberCouponRequestDto;
import ticketing.ticket.member.service.MemberCouponService;
import ticketing.ticket.exception.DuplicatedCouponException;

@RestController
@RequestMapping("member-coupon")
public class MemberCouponController {
    private final MemberCouponService memberCouponService;

    public MemberCouponController(MemberCouponService memberCouponService) {
        this.memberCouponService = memberCouponService;
    }

    
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> save(@RequestBody MemberCouponRequestDto memberCouponRequestDto) throws DuplicatedCouponException {
         memberCouponService.saveMemberCoupon(memberCouponRequestDto);
         return ResponseEntity.ok().build();
    }

    @GetMapping("/all/{memberId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberCouponService.findAllByMemberId(memberId));
    }



}
