package com.bookmyshow.bookmyshow.dtos;

import com.bookmyshow.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDto {
    private ResponseStatus responseStatus;
    private int amount;
    private Long bookingId;

}
