package com.bookmyshow.bookmyshow.repository;

import com.bookmyshow.bookmyshow.models.Show;
import com.bookmyshow.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findById(List<Long> showSeatIds);

    @Override
    ShowSeat save(ShowSeat showSeat);
}
