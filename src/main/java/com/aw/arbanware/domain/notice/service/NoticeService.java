package com.aw.arbanware.domain.notice.service;

import com.aw.arbanware.domain.notice.entity.Notice;
import com.aw.arbanware.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> noticeList(){
        return noticeRepository.findAll();
    }

    public Optional<Notice> noticeDetail(Long id) {
        return noticeRepository.findById(id);
    }

}
