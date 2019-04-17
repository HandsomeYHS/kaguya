package com.akanemurakawa.kaguya.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.Notice;
import com.hanaeyuuma.freeblogs.service.NoticeService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-7-30
 */
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 *  分页查询公告
	 * @param pn
	 * @return
	 */
	@RequestMapping("/notice")
	@ResponseBody
	public Msg getNoticeWithAjax(@RequestParam(value="pn", defaultValue = "1")Integer pn) {
		PageHelper.startPage(pn, 10);
		List<Notice> notices = noticeService.selectAllWithAdmin();
		PageInfo page = new PageInfo(notices, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 写公告的时候验证标题是否存在
	 * @param title
	 * @return
	 */
	@RequestMapping("/checkNoticeTitleExists")
	@ResponseBody
	public Msg checkTitleExists(@RequestParam(value="title")String title){
		Integer notice = noticeService.checkExistTitle(title);
		if (notice != null){
			return Msg.fail();
		}
		return Msg.success();
	}
	
}
