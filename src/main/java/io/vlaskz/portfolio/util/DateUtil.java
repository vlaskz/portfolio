package io.vlaskz.portfolio.util;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
public class DateUtil {

    public String convertLocalDateTimeToServerFormal(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("hh:mm:ss dd.MM.yyyy").format(localDateTime);
    }

}
