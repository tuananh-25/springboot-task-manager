package com.example.taskmanager.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaginationResponse<T> {
    private List<T> items;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
