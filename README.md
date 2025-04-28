# FutsalGG_Android Application

풋살 매치 관리 및 팀 관리 애플리케이션

## 프로젝트 구조

### Clean Architecture
- Presentation Layer: UI 관련 코드
- Domain Layer: 비즈니스 로직
- Data Layer: 데이터 소스 및 리포지토리 구현
- Remote Layer: API 통신

### 주요 기능
1. 인증 (Auth)
   - Google 로그인
   - 토큰 관리
   - 자동 로그인

2. 팀 관리 (Team)
   - 팀 생성/수정/삭제
   - 팀원 관리
   - 팀 통계

3. 매치 관리 (Match)
   - 매치 생성/수정/삭제
   - 매치 통계
   - 선수별 기록

4. 사용자 관리 (User)
   - 프로필 관리
   - 알림 설정

### 기술 스택
- Kotlin
- Jetpack Compose
- MVVM Architecture
- Clean Architecture
- Dagger Hilt
- Retrofit
- Firebase
- Coroutines
- Flow

### 디렉토리 구조
```
app/
├── presentation/     # UI 관련 코드
│   ├── auth/        # 인증 화면
│   ├── main/        # 메인 화면
│   ├── match/       # 매치 관련 화면
│   ├── team/        # 팀 관련 화면
│   ├── user/        # 사용자 관련 화면
<<<<<<< HEAD
│   └── common/      # 공통 컴포넌트
=======
│   └── common/      # 공통 UI 컴포넌트
>>>>>>> 3ba486f (Recent match date 구현 MainScreen)
│
├── domain/          # 비즈니스 로직
│   ├── auth/        # 인증 도메인
│   ├── match/       # 매치 도메인
│   ├── team/        # 팀 도메인
│   ├── user/        # 사용자 도메인
│   └── common/      # 공통 도메인
│
├── data/            # 데이터 소스
│   ├── auth/        # 인증 데이터
│   ├── match/       # 매치 데이터
│   ├── team/        # 팀 데이터
│   ├── user/        # 사용자 데이터
│   └── common/      # 공통 데이터
│
├── remote/          # API 통신
│   ├── api/         # API 인터페이스
│   └── model/       # API 모델
│
├── di/              # 의존성 주입
├── util/            # 유틸리티
├── core/            # 코어 기능
└── framework/       # 프레임워크 관련
```

### 주요 라이브러리
```gradle
dependencies {
    // AndroidX
    implementation "androidx.core:core-ktx:1.12.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
    implementation "androidx.activity:activity-compose:1.8.2"
    
    // Compose
    implementation platform("androidx.compose:compose-bom:2024.02.00")
    implementation "androidx.compose.ui:ui"
    implementation "androidx.compose.ui:ui-graphics"
    implementation "androidx.compose.ui:ui-tooling-preview"
    implementation "androidx.compose.material3:material3"
    
    // Hilt
    implementation "com.google.dagger:hilt-android:2.50"
    kapt "com.google.dagger:hilt-android-compiler:2.50"
    
    // Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    
    // Firebase
    implementation platform("com.google.firebase:firebase-bom:32.7.2")
    implementation "com.google.firebase:firebase-auth-ktx"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    
    // Testing
    testImplementation "junit:junit:4.13.2"
    androidTestImplementation "androidx.test.ext:junit:1.1.5"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
    androidTestImplementation platform("androidx.compose:compose-bom:2024.02.00")
    androidTestImplementation "androidx.compose.ui:ui-test-junit4"
    debugImplementation "androidx.compose.ui:ui-tooling"
    debugImplementation "androidx.compose.ui:ui-test-manifest"
}
```

### 설치 및 실행
1. 프로젝트 클론
```bash
git clone [repository-url]
```

2. 프로젝트 열기
- Android Studio에서 프로젝트를 엽니다.

3. 빌드 및 실행
- Android Studio의 Run 버튼을 클릭하거나 `./gradlew assembleDebug` 명령어를 실행합니다.

### 개발 환경
- Android Studio Hedgehog | 2023.1.1
- Kotlin 1.9.0
- Gradle 8.2
- Android SDK 34
- JDK 17

### 라이센스
이 프로젝트는 MIT 라이센스를 따릅니다.
