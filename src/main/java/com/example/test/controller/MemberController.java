package com.example.test.controller;

import com.example.test.assembler.MemberAssembler;
import com.example.test.dao.Member;
import com.example.test.dto.MemberForm;
import com.example.test.repository.MemberRepository;
import com.example.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hateos")
public class MemberController {
    private final MemberRepository repo;
    private final MemberService service;
    private final MemberAssembler assembler;
    @GetMapping("/members")
    public CollectionModel<EntityModel<Member>> all()
    {
        List<Member> list = repo.findAll();
        List<EntityModel<Member>> entity = list.stream().map(assembler :: toModel).collect(Collectors.toList());
        return CollectionModel.of(entity,
                linkTo(methodOn(MemberController.class).all()).withSelfRel());

    }

    @GetMapping("/members/{id}")
    public EntityModel<Member> one(@PathVariable("id")long id)
    {
        Member member = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("id"+id));
        return assembler.toModel(member);
    }

    @PostMapping(value = "/members", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createJson(@RequestBody MemberForm form)
    {
        Member member = new Member(form.getName(),form.getEmail(),form.getRegion());
        EntityModel<Member> entity = assembler.toModel(repo.save(member));
        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF)
                .toUri()).body(entity);
    }

    @PostMapping(value = "/members", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createForm(@RequestBody MemberForm form)
    {
        Member member = new Member(form.getName(),form.getEmail(),form.getRegion());
        EntityModel<Member> entity = assembler.toModel(repo.save(member));
        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF)
                .toUri()).body(entity);
    }

    @PutMapping(value="/members/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeMemberJson(@RequestBody MemberForm form, @PathVariable("id") long id)
    {
        Member member = service.update(form, id);
        EntityModel<Member> entity = assembler.toModel(member);
        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entity);
    }

    @PutMapping(value="/members/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> changeMemberForm(MemberForm form, @PathVariable("id") long id)
    {
        Member member = service.update(form, id);
        EntityModel<Member> entity = assembler.toModel(member);
        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entity);
    }

    @DeleteMapping(value = "/members/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") long id)
    {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
