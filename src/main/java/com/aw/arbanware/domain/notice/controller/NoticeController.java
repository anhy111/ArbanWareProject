package com.aw.arbanware.domain.notice.controller;

import com.aw.arbanware.domain.notice.entity.Notice;
import com.aw.arbanware.domain.notice.service.NoticeService;
import com.aw.arbanware.global.config.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public String noticeList(Model model) {
        List<Notice> noticeList = noticeService.noticeList();
        model.addAttribute("noticeList", noticeList);
        return "page/notice/list";
    }

    @GetMapping("/{id}")
    public String noticeDetail(@PathVariable("id")Long id, Model model){
        //이 뜻은 id값을 통해서 찾았는데 만약 해당 id값이 없다면 null을 반환해라 라는의미입니다.
        Notice noticeDetail = noticeService.noticeDetail(id).orElse(null);
        model.addAttribute("noticeDetail", noticeDetail);
        return "page/notice/detail";
    }


    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String noticeRegister(@AuthenticationPrincipal SecurityUser securityUser, Model model){
        String name = securityUser.getName();
        model.addAttribute("name", name);
        return "page/notice/register";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String noticeRegisterPost(@RequestBody Notice notice){
        noticeService.noticeRegister(notice);
        return "redirect:/notice";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String noticeDelete(@PathVariable("id")Long id){
        noticeService.noticeDelete(id);
        return "redirect:/notice";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String noticeUpdate(@PathVariable("id")Long id, Model model, @AuthenticationPrincipal SecurityUser securityUser){
        Notice noticeDetail = noticeService.noticeDetail(id).orElse(null);
        String name = securityUser.getName();
        model.addAttribute("noticeDetail", noticeDetail);
        model.addAttribute("name", name);
        return "page/notice/update";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String noticeUpdate(@PathVariable("id")Long id, @RequestBody Notice notice){
        noticeService.noticeDelete(id);
        return "redirect:/notice/{id}/edit";
    }
}
