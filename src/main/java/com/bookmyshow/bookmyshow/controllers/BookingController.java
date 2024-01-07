package com.bookmyshow.bookmyshow.controllers;

import com.bookmyshow.bookmyshow.dtos.BookingRequestDto;
import com.bookmyshow.bookmyshow.dtos.BookingResponseDto;
import com.bookmyshow.bookmyshow.models.Booking;
import com.bookmyshow.bookmyshow.models.ResponseStatus;
import com.bookmyshow.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired
    BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }
    BookingResponseDto bookMovie(BookingRequestDto requestDto){
        BookingResponseDto bookingResponseDto=new BookingResponseDto();
        try {
            Booking booking=bookingService.bookMovie(
                    requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatIds()
            );
            bookingResponseDto.setBookingId(booking.getId());
            bookingResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        return bookingResponseDto;
    }
}

