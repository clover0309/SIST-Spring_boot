package com.example.bbs_0220.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BbsVO {
  private String b_idx, subject, writer, content, file_name, ori_name, write_date, ip, hit, bname, status;
  // 댓글들
  private List<CommVO> c_list;
  // 첨부파일
  private MultipartFile file;
}
