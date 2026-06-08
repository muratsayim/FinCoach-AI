@echo off
title FinCoachAI - Kişisel Finans Yönetim Sistemi
echo FinCoachAI baslatiliyor, lutfen bekleyin...
cd /d "%~dp0"

:: Tarayiciyi 4 saniye sonra otomatik olarak asenkron sekilde ac
start "" cmd /c "timeout /t 4 >nul && start http://localhost:8080"

:: Sistemde mvn (Maven) komutu kurulu mu kontrol et
where mvn >nul 2>nul
if %errorlevel% equ 0 (
    :: Eğer sistemde Maven kuruluysa global komutu kullan
    mvn spring-boot:run
) else (
    :: Eğer kurulu değilse lokal klasördeki Maven'ı göreceli (relative) yol ile kullan
    ..\maven\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
)

if %errorlevel% neq 0 (
    echo.
    echo [HATA] FinCoachAI baslatilamadi veya beklenmedik bir hata ile sonlandi.
    pause
)
