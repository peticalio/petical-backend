package org.majimena.petz.web.rest;

import org.majimena.petz.repository.UserRepository;
import org.majimena.petz.service.MailService;
import org.majimena.petz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

//    /**
//     * GET  /account -> get the current user.
//     */
//    @RequestMapping(value = "/account",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<UserDTO> getAccount() {
//        return Optional.ofNullable(userService.getUserByUserId())
//            .map(user -> {
//                return new ResponseEntity<>(
//                    new UserDTO(
//                        user.getLogin(),
//                        null,
//                        user.getFirstName(),
//                        user.getLastName(),
//                        user.getEmail(),
//                        user.getLangKey(),
//                        user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList())),
//                    HttpStatus.OK);
//            })
//            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }

//    /**
//     * POST  /account -> update the current user information.
//     */
//    @RequestMapping(value = "/account",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
//        return userRepository
//            .findOneByLogin(userDTO.getLogin())
//            .filter(u -> u.getLogin().equals(SecurityUtils.getCurrentLogin()))
//            .map(u -> {
//                userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail());
//                return new ResponseEntity<String>(HttpStatus.OK);
//            })
//            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
//
//    /**
//     * POST  /change_password -> changes the current user's password
//     */
//    @RequestMapping(value = "/account/change_password",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<?> changePassword(@RequestBody String password) {
//        if (StringUtils.isEmpty(password) || password.length() < 5 || password.length() > 50) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        userService.changePassword(password);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/account/reset_password/init",
//        method = RequestMethod.POST,
//        produces = MediaType.TEXT_PLAIN_VALUE)
//    @Timed
//    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {
//
//        return userService.requestPasswordReset(mail)
//            .map(user -> {
//                String baseUrl = request.getScheme() +
//                    "://" +
//                    request.getServerName() +
//                    ":" +
//                    request.getServerPort();
//            mailService.sendPasswordResetMail(user, baseUrl);
//            return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
//            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
//
//    }
//
//    @RequestMapping(value = "/account/reset_password/finish",
//        method = RequestMethod.POST,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<String> finishPasswordReset(@RequestParam(value = "key") String key, @RequestParam(value = "newPassword") String newPassword) {
//        return userService.completePasswordReset(newPassword, key)
//              .map(user -> new ResponseEntity<String>(HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
}
