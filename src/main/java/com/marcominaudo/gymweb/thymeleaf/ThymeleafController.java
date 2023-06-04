package com.marcominaudo.gymweb.thymeleaf;

import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackDTO;
import com.marcominaudo.gymweb.controller.dto.feedback.SearchFeedbackDTO;
import com.marcominaudo.gymweb.controller.dto.user.SearchUserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserRequestDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserResponseDTO;
import com.marcominaudo.gymweb.controller.dto.workoutPlan.SearchWorkoutPlansDTO;
import com.marcominaudo.gymweb.exception.model.ErrorMessage;
import com.marcominaudo.gymweb.security.controller.dto.LoginResponseDTO;
import com.marcominaudo.gymweb.security.controller.dto.RequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ThymeleafController {
    RestTemplate restTemplate = new RestTemplate();
    
    @Autowired
    SerializeException serializeException;
    
    // Login
    @RequestMapping("/login")
    public String login(Model model) {
        RequestDTO requestDTO = new RequestDTO();
        model.addAttribute("requestDTO", requestDTO);
        return "login.html";
    }

    @PostMapping("/login")
    public String loginform(Model model, @ModelAttribute("requestDTO") RequestDTO requestDTO, HttpServletResponse response){
        // Set request object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        // Use api
        try {
            LoginResponseDTO token = (LoginResponseDTO) createPostAuthenticatedRequest("", "http://localhost:8080/api/v1/auth/login", LoginResponseDTO.class, requestDTO);
            // Create a cookie for token
            Cookie cookie = new Cookie("jwtToken", token.getJWTToken());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60); // Durata del cookie di un giorno
            response.addCookie(cookie);
        }
        catch(Exception e){
            model.addAttribute("loginError", true);
            ErrorMessage errorMessage = serializeException.serialize(e);
            model.addAttribute("messageError", errorMessage.getMessage());
        }

        return "redirect:homePage";
    }

    @RequestMapping("/homePage")
    public String homePage(Model model, @CookieValue(value = "jwtToken", required = false) String jwt) {
        if(!checkJWT(jwt))
            return "redirect:login";

        UserResponseDTO user = (UserResponseDTO) createGetAuthenticatedRequest(jwt,"http://localhost:8080/api/v1/user" ,UserResponseDTO.class);
        SearchUserBodyDetailsDTO searchUserBodyDetailsDTO = (SearchUserBodyDetailsDTO) createGetAuthenticatedRequest(jwt,"http://localhost:8080/api/v1/user/bodyDetails" , SearchUserBodyDetailsDTO.class);
        SearchWorkoutPlansDTO searchWorkoutPlansDTO = (SearchWorkoutPlansDTO) createGetAuthenticatedRequest(jwt,"http://localhost:8080/api/v1/workout/all" , SearchWorkoutPlansDTO.class);
        //SearchFeedbackDTO searchFeedbackDTO = (SearchFeedbackDTO) createGetAuthenticatedRequest(jwt,"http://localhost:8080/api/v1/feedback" , SearchFeedbackDTO.class); //Todo: serve uuid del pt

        model.addAttribute("user", user);
        model.addAttribute("listUserBodyDetailsDTO", searchUserBodyDetailsDTO.getUserBodyDetails());
        model.addAttribute("userBodyDetailsDTO", new UserBodyDetailsDTO());
        model.addAttribute("workoutPlansDTO", searchWorkoutPlansDTO.getWorkoutPlans());
        model.addAttribute("feedbackDTO", new FeedbackDTO());
        //model.addAttribute("listFeedbackDTO", searchFeedbackDTO.getFeedbacks());

        return "homePage.html";
    }

    @PostMapping("/bodyDetails")
    public String bodyDetails(@ModelAttribute("userBodyDetailsDTO") UserBodyDetailsDTO userBodyDetailsDTO, @CookieValue(value = "jwtToken", required = false) String jwt){
        if(!checkJWT(jwt))
            return "redirect:login";

        createPostAuthenticatedRequest(jwt, "http://localhost:8080/api/v1/user/bodyDetails", null, userBodyDetailsDTO);
        return "redirect:homePage";
    }

    @RequestMapping("/privacy")
    public String privacy(@RequestParam("privacy") Boolean privacy, @CookieValue(value = "jwtToken", required = false) String jwt){
        if(!checkJWT(jwt))
            return "redirect:login";

        createGetAuthenticatedRequest(jwt, "http://localhost:8080/api/v1/user/privacy?value=" + privacy, null);
        return "redirect:homePage";
    }

    @PostMapping("/feedback")
    public String feedback(@ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO, @CookieValue(value = "jwtToken", required = false) String jwt){
        if(!checkJWT(jwt))
            return "redirect:login";

        createPostAuthenticatedRequest(jwt, "http://localhost:8080/api/v1/feedback", null, feedbackDTO);
        return "redirect:homePage";
    }



    private boolean checkJWT(String jwt) {
        // Set request object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Boolean> request = new HttpEntity<>(headers);

        return restTemplate.exchange("http://localhost:8080/api/v1/auth/validateToken/"+ jwt, HttpMethod.GET, request, Boolean.class).getBody();
    }

    private Object createGetAuthenticatedRequest(String jwt, String url,  Class returnType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<Object> request =  new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, returnType).getBody();
    }

    private Object createPostAuthenticatedRequest(String jwt, String url, Class returnType, Object requestObject) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<Object> request =  new HttpEntity<>(requestObject, headers);
        return restTemplate.exchange(url, HttpMethod.POST, request, returnType).getBody();
    }

}
