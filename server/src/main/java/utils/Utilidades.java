package utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.google.protobuf.Timestamp;

public class Utilidades {
	
	//conversion de fechas
	public LocalDateTime toLocalDateTime(Timestamp timestamp) {
	    Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
	    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
	}
	
	public Timestamp fromLocalDateTime(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
