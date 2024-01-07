package com.bookmyshow.bookmyshow.services;

import com.bookmyshow.bookmyshow.exceptions.InvalidUserException;
import com.bookmyshow.bookmyshow.exceptions.ShowNotAvailableException;
import com.bookmyshow.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.bookmyshow.bookmyshow.models.*;
import com.bookmyshow.bookmyshow.repository.ShowRepository;
import com.bookmyshow.bookmyshow.repository.ShowSeatRepository;
import com.bookmyshow.bookmyshow.repository.ShowSeatTypeRepository;
import com.bookmyshow.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    PriceCalculatorService priceCalculatorService;
    private ShowSeatTypeRepository showSeatTypeRepository;
    @Autowired
    BookingService(UserRepository userRepository,
                   ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository,
                   ShowSeatTypeRepository showSeatTypeRepository){
        this.userRepository=userRepository;
        this.showRepository=showRepository;
        this.showSeatRepository=showSeatRepository;
        this.priceCalculatorService=new PriceCalculatorService(showSeatTypeRepository);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws Exception {
        //steps :
        /*
        ----------Start the transaction-----------
        1: get the user from userId
        2: get the show from showId
        3: get the showseats from list of showSeatIds.
        4: Check if all the show seats are available.
        ---- Take DB lock-----
        5: If yes, Mark the show seat status as blocked.
        6: Save the updated status to DB.
        ----- release DB lock-------------
        7: Create the Booking Object.. (Go to the payments page..)
        8: Return the Booking Object.
        -----------Release the lock-------------
         */
        // 1.get the user by user id.
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new InvalidUserException("invalid user id");
        }
        User bookedBy = optionalUser.get();
        //2. fetch the show details
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowNotAvailableException("show not available");
        }
        Show show= optionalShow.get();
        //3. get the showseats from list of showseatsid
        List<ShowSeat> showSeats= showSeatRepository.findById(showSeatIds);

        //4. Check if all the show seats are available
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new ShowSeatNotAvailableException("show seat is not available");
            }
        }
        // 5 If yes, Mark the show seat status as Blocked.
        // 6.Save the updated object in the db.
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
        //7. create the booking object.
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setShow(show);
        booking.setCreatedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setPaymentList(new ArrayList<>());
        booking.setAmount(priceCalculatorService.calculatePrice(savedShowSeats,show));
        return booking;
    }
}
