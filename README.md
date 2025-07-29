### 작업 프로세스

1. `main` 브랜치로부터 새로운 브랜치 생성  
   예: `git checkout -b feature/회원가입`

2. 기능 개발 완료 후 `main` 브랜치 대상으로 PR 생성  
   예: GitHub에서 `feature/회원가입 → main`

3. 다음 조건 충족 시에만 병합 가능:
    - 충돌 없음
    - Actions 체크(빌드/배포) 성공
    - 최신 main 기준으로 up-to-date
    - 리뷰 승인 1건 이상
    - 코멘트 전부 resolve

4. PR이 `main`에 머지되면 → 자동으로 EC2에 배포됨

---

### 예외 상황

- **배포 실패 시**: `Require deployments to succeed`로 인해 병합 차단됨
- **Actions 체크 안 뜰 경우**: 워크플로우 이름 확인 (`name:`), 한 번 이상 실행 필요

---

### 참고
- Actions 워크플로우 이름: `Deploy to EC2`
- 운영 환경 이름 (GitHub Environments): `production`
