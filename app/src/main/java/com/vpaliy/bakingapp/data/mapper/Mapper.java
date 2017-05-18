package com.vpaliy.bakingapp.data.mapper;


public interface Mapper<To,From> {
    To map(From from);
    From reverseMap(To to);
}
