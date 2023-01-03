package ra.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.Erole;
import ra.model.entity.Roles;
import ra.model.entity.Users;
import ra.model.service.RoleService;
import ra.model.service.UserService;
import ra.payload.reponse.JwtResponse;
import ra.payload.reponse.MessageResponse;
import ra.payload.request.LoginRequest;
import ra.payload.request.SignupRequest;
import ra.payload.request.UserRequest;
import ra.security.CustomUserDetails;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> GetAllProduct() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Users getUserById(@PathVariable("userId") int id) {
        return userService.findById(id);
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setCreated(sdf.parse(strNow));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(Erole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/signin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
            String jwt = tokenProvider.generateToken(customUserDetail);
            if (!customUserDetail.isUserStatus()){
                return ResponseEntity.badRequest().body("tài khoản tạm thời bị khóa không thể đăng nhập");
            }else {
                //Lay cac quyen cua user
                List<String> listRoles = customUserDetail.getAuthorities().stream()
                        .map(item -> item.getAuthority()).collect(Collectors.toList());
                return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(),
                        customUserDetail.getPhone(), listRoles));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("tài khoản hoặc mật khẩu sai");
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int id) {
           try {
               Users userDelete = userService.findById(id);
               userDelete.setUserStatus(false);
               userService.saveOrUpdate(userDelete);
               return ResponseEntity.ok("khóa tài khoản thành công");
           }catch (Exception e){
               e.printStackTrace();
           }
           return ResponseEntity.ok("khóa tài khoản thất bại");
    }
    @PostMapping("/unlock/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>unlockUser(@PathVariable("userId") int id){
        try {
            Users unlockUser = userService.findById(id);
            unlockUser.setUserStatus(true);
            userService.saveOrUpdate(unlockUser);
            return ResponseEntity.ok("mở khoá thành công");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("có lỗi trong quá trình xử lý vui lòng thử lại sau");
    }
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") int id, @RequestBody UserRequest user){
        try {
            Users userUpdate = userService.findById(id);
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPhone(user.getPhone());
            userService.saveOrUpdate(userUpdate);
            return ResponseEntity.ok("cập nhật thành công");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("cập nhật thất bại");
    }
}