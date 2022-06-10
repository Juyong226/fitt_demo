package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
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
    public Long join(MemberJoinDto joinDto) throws IllegalStateException {
        // 닉네임 중복 검사 비밀번호 인코딩
        String nickname = joinDto.getNickname();
        if (!validateMember(nickname)) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
        String password = passwordEncoder.encode(joinDto.getPassword());
//        LocalDate dateOfBirth =
//                LocalDate.of(joinDto.getYearOfBirth(), joinDto.getMonthOfBirth(), joinDto.getDateOfBirth());
        Member member = Member.create(joinDto.getName(), nickname, password, joinDto.getDateOfBirth(), LocalDate.now());
        memberRepository.save(member);
        return member.getId();
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

    private boolean validateMember(String nickname) {
        if (nickname == null) {
            throw new IllegalStateException("닉네임을 입력해주세요.");
        }
        List<Member> result = memberRepository.findByNickname(nickname);
        return (result.size() == 0);
    }

    private MemberResponseDto entityToResponseDto(Member member) {
        if (member == null) return null;
        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.initialize(member);
        return responseDto;
    }
}
