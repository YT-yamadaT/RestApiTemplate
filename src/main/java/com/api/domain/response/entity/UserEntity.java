package com.api.domain.response.entity;

import java.util.List;

import com.api.domain.response.Response;
import com.api.domain.response.data.UserData;

import lombok.Data;

@Data
public class UserEntity implements Response{

	List<UserData> users;
}
