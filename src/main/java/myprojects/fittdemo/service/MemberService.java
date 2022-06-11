package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.MemberJoinDto;
import myprojects.fittdemo.controller.dtos.MemberRequestDto;
import myprojects.fittdemo.controller.dtos.MemberResponseDto;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Member 가입 메서드
     * @param: MemberJoinDto
     * @return: Long memberId
     * */
    public Long join(MemberJoinDto joinDto) {
        String password = passwordEncoder.encode(joinDto.getPassword());
        LocalDate dateOfBirth = LocalDate.parse(joinDto.getDateOfBirth());
        Member member = Member.create(joinDto.getName(), joinDto.getNickname(), password, dateOfBirth, LocalDate.now());
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * Member 검증 메서드
     *  - 멤버의 닉네임의 중복 여부를 확인한다.
     * @param: String nickname
     * @return: Boolean
     * */
    public boolean validateMember(String nickname) throws Exception {
        if (nickname == null || nickname == "") {
            log.info("nickname is null !");
            throw new IllegalStateException("닉네임을 입력해주세요.");
        }
        if (memberRepository.findByNickname(nickname).size() > 0) {
            log.info("nickname is already used !");
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        return true;
    }

    public Long login(MemberRequestDto requestDto) throws Exception {
        List<Member> findList = memberRepository.findByNickname(requestDto.getNickname());
        if (findList.size() == 0) {
            throw new IllegalStateException("존재하지 않는 회원입니다. 닉네임을 확인해주세요.");
        }
        Member findOne = findList.get(0);
        if (passwordEncoder.matches(requestDto.getPassword(), findOne.getPassword()) == false) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return findOne.getId();
    }

    public MemberResponseDto find(Long memberId) {
        Member findOne = memberRepository.findOne(memberId);
        return entityToResponseDto(findOne);
    }

    public MemberResponseDto findWithBigFours(Long memberId) {
        List<Member> members = memberRepository.findWithBigFours(memberId);
        return entityToResponseDto(members.get(0));
    }

    /**
     * Member 상태 필드 수정 메서드
     * @param: MemberRequestDto
     * @return: MemberResponseDto
     * */
    public MemberResponseDto update(MemberRequestDto requestDto) {
        Member findOne = memberRepository.findOne(requestDto.getMemberId());
        LocalDate dateOfBirth =
                LocalDate.of(requestDto.getYearOfBirth(), requestDto.getMonthOfBirth(), requestDto.getDateOfBirth());
        findOne.update(dateOfBirth);
        return entityToResponseDto(findOne);
    }

    /**
     * Member 탈퇴 메서드
     * @param: Long memberId
     * */
    public void withdraw(Long memberId) {
        Member findOne = memberRepository.findOne(memberId);
        memberRepository.remove(findOne);
    }

    //------------------------------------------------------------------------------------------------------------------

    private MemberResponseDto entityToResponseDto(Member member) {
        if (member == null) return null;
        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.initialize(member);
        return responseDto;
    }
}
