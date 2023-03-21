package com.sparta.parknav.booking.controller;

import com.sparta.parknav.booking.dto.MyBookingResponseDto;
import com.sparta.parknav.booking.service.BookingService;
import com.sparta.parknav.booking.dto.BookingInfoRequestDto;
import com.sparta.parknav.booking.dto.BookingInfoResponseDto;
import com.sparta.parknav.booking.dto.BookingResponseDto;
import com.sparta.parknav._global.response.ApiResponseDto;
import com.sparta.parknav._global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{park-id}")
    public ApiResponseDto<BookingInfoResponseDto> getInfoBeforeBooking(@PathVariable(name = "park-id") Long id, BookingInfoRequestDto requestDto) {
        return bookingService.getInfoBeforeBooking(id, requestDto);
    }

    @PostMapping("/{park-id}")
    public ApiResponseDto<BookingResponseDto> bookingPark(@PathVariable(name = "park-id") Long id,
                                                          @RequestBody BookingInfoRequestDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookingService.bookingPark(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{booking-id}")
    public ApiResponseDto<Void> cancelBooking(@PathVariable(name = "booking-id") Long id,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bookingService.cancelBooking(id, userDetails.getUser());
    }

    @GetMapping("/mypage")
    public ApiResponseDto<Page<MyBookingResponseDto>> getMyBooking(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                   @RequestParam int page,
                                                                   @RequestParam int size) {
        return bookingService.getMyBooking(userDetails.getUser(),page-1, size);
    }

}
