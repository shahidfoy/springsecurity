package com.shahidfoy.springsecurity.controller;

import com.shahidfoy.springsecurity.model.Notice;
import com.shahidfoy.springsecurity.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticesController {

	@Autowired
	private NoticeRepository noticeRepository;

	@GetMapping("/notices")
	public List<Notice> getNotices() {
		List<Notice> notices = noticeRepository.findAllActiveNotices();
		if (notices != null ) {
			System.out.println("notices found " + notices.size());
			return notices;
		}else {
			System.out.println("no notices");
			return null;
		}
	}

}
