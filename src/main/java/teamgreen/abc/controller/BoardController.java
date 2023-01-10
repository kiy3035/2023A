package teamgreen.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import teamgreen.abc.domain.Board1;
import teamgreen.abc.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 작성
    @GetMapping("/board/write")     // localhost:8080/board/write
    public String boardWriteForm() {

        return "thymeleaf/board/boardwrite";
    }
    @PostMapping("/board/writeprocess")
    // @PostMapping("/board/writeprocess") 얘랑
    // boardwrite.html에 있는
    // form action="/board/writeprocess" 같아야함

    public String boardWriteProcess(Board1 board1, Model model, MultipartFile file) throws Exception {

        boardService.write(board1, file);

        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");

        System.out.println("글 번호 : " + board1.getPostidx());
        System.out.println("제목 : " + board1.getPosttitle());
        System.out.println("작성자 : " + board1.getPostwriter());
        System.out.println("작성시간 : " + board1.getPostregdate());
        System.out.println("글 내용 : " + board1.getPostcont());
        System.out.println("게시판 분류 : " + board1.getPostmenuidx());

        return "thymeleaf/board/boardmessage";
    }

    // 게시글 목록
    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "postidx", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<Board1> list = null;

        if(searchKeyword == null) {
            list = boardService.board1List(pageable);
        } else {
            list = boardService.board1SearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "thymeleaf/board/boardlist_css";
    }

    // 게시글 상세 페이지
    @GetMapping("/board/view")  // localhost:8080/board/view?postidx=1
    public String boardView(Model model, Long postidx) {

        model.addAttribute("board1", boardService.boardView(postidx));
        return "thymeleaf/board/boardview";
    }

    // 게시글 삭제
    @GetMapping("/board/delete")
    public String boardDelete(Long postidx) {

        boardService.boardDelete(postidx);
        return "redirect:/board/list";
    }

    // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    // 게시글 수정 , @PathVariable 사용 - 기존 쿼리string이랑은 약간 다름! // 푸시랑 머지랑 헷갈리지 말자
    @GetMapping("/board/modify/{postidx}")
    public String boardModify(@PathVariable("postidx") Long postidx,
                              Model model) {

        model.addAttribute("board1", boardService.boardView(postidx));
        return "thymeleaf/board/boardmodify";
    }

    @PostMapping("/board/update/{postidx}")
    public String boardUpdate(@PathVariable("postidx") Long postidx, Board1 board1, MultipartFile file) throws Exception {

        // 기존에 있던 글이 담겨서 넘어옴
        Board1 boardTemp = boardService.boardView(postidx);

        // 새로 입력한 내용을 기존 내용위에 덮어쓰기
        boardTemp.setPosttitle(board1.getPosttitle());
        boardTemp.setPostcont(board1.getPostcont());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
    // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

    // [등록 - 헬퍼 & 아이 ]
    // 아이 등록 게시판
    @GetMapping("/register/reg_child")     // localhost:8080/board/write
    public String regChild() {

        return "thymeleaf/register/reg_child";
    }
}
