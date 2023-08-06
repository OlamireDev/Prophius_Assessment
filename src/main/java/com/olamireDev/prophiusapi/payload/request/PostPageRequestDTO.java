package com.olamireDev.prophiusapi.payload.request;

import com.olamireDev.prophiusapi.enums.PostSortingParameter;

public record PostPageRequestDTO(long userId, int pageNumber, int pageSize, boolean sorted, PostSortingParameter parameter) {
}
