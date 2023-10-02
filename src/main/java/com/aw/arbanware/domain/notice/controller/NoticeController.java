package com.aw.arbanware.domain.notice.controller;

import com.aw.arbanware.domain.notice.entity.Notice;
import com.aw.arbanware.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String noticeList(Model model) {
        List<Notice> noticeList = noticeService.noticeList();
        model.addAttribute("noticeList", noticeList);
        return "notice/list";
    }

    @GetMapping("/detail/{id}")
    public String noticeDetail(@PathVariable("id")Long id, Model model){
        //이 뜻은 id값을 통해서 찾았는데 만약 해당 id값이 없다면 null을 반환해라 라는의미입니다.
        Notice noticeDetail = noticeService.noticeDetail(id).orElse(null);
        model.addAttribute("noticeDetail", noticeDetail);
        return "notice/detail";
    }
}
