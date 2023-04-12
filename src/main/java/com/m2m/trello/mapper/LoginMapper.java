package com.m2m.trello.mapper;

import com.m2m.trello.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface LoginMapper {

	public MemberDto findByEmail(String loginId);

	public int save(MemberDto memberDto);
}
