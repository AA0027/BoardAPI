package com.example.test.assembler;

import com.example.test.controller.MemberController;
import com.example.test.dao.Member;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberAssembler implements RepresentationModelAssembler<Member, EntityModel<Member>> {
    @Override
    public EntityModel<Member> toModel(Member entity) {
        return EntityModel.of(entity
                ,linkTo(methodOn(MemberController.class).one(entity.getId())).withSelfRel()
                ,linkTo(methodOn(MemberController.class).all()).withRel("members"));

    }
}
