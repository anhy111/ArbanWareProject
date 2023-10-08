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
    } //공지사항 목록 조회

    public Optional<Notice> noticeDetail(Long id) {
        return noticeRepository.findById(id);
    } //공지사항 상세 조회

    public Notice noticeRegister(Notice notice){
        return noticeRepository.save(notice);
    } //공지사항 등록

    public void noticeDelete(Long id){
        noticeRepository.deleteById(id);
    } //공지사항 삭제

    public void noticeUpdate(Long id){
        Optional<Notice> notice = noticeRepository.findById(id);
        Notice noticeDetail = notice.get();

    }//공지사항 수정

}
