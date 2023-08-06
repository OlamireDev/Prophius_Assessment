package com.olamireDev.prophiusapi.payload.request;

import com.olamireDev.prophiusapi.enums.PostSortingParameter;

public record PostSearchDTO(String searchWith, boolean searchByUser, PostSortingParameter parameter, int pageNumber, int pageSize) {
}
