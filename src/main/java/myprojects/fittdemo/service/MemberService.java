package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.MemberJoinDto;
import myprojects.fittdemo.controller.MemberRequestDto;
import myprojects.fittdemo.controller.MemberResponseDto;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Member 가입 메서드
     * @param: MemberJoinDto
     * @return: Long memberId
     * */
    public Long join(MemberJoinDto param) {
        LocalDate dateOfBirth = LocalDate.of(param.getYear(), param.getMonth(), param.getDayOfMonth());
        Member member = Member.create(param.getName(), dateOfBirth, LocalDate.now());
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
    public MemberResponseDto update(MemberRequestDto param) {
        Member findOne = memberRepository.findOne(param.getMemberId());
        LocalDate dateOfBirth = LocalDate.of(param.getYear(), param.getMonth(), param.getDayOfMonth());
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
