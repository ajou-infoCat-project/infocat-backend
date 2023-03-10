package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.domain.dto.SignUpRequest;
import backend.resumerryv2.auth.service.AuthService;
import backend.resumerryv2.auth.service.EmailService;
import backend.resumerryv2.exception.validation.ValidationSequence;
import backend.resumerryv2.global.dto.GlobalResponse;
import backend.resumerryv2.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<GlobalResponse> signUp(
            @RequestHeader("validation-token") String validationToken ,
            @Validated(ValidationSequence.class) @RequestBody SignUpRequest signUpRequest
            ){
        jwtProvider.verify(validationToken);
        EmailValidation e = emailService.checkValidationCode(signUpRequest.getEmail(), Integer.valueOf(jwtProvider.decordToken(validationToken)));
        authService.signUp(signUpRequest);
        emailService.deleteValidationCode(e);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping("/validation")
    public ResponseEntity<GlobalResponse> checkValidation(
            @Validated(ValidationSequence.class) @RequestBody SignUpRequest signUpRequest
    ){
        authService.checkDuplicatedUser(signUpRequest.getEmail());
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }


}
