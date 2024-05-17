package org.example.searchservice3;

import java.time.LocalDateTime;

public record MessageAndTimeRecord(LocalDateTime time, String message) implements MessageRecord {}
