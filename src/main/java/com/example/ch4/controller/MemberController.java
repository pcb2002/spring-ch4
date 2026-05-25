package com.example.ch4.controller;

import com.example.ch4.dto.MemberDto;
import com.example.ch4.entity.Member;
import com.example.ch4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> save(@RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.saveMember(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }
}