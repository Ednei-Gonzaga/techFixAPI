package com.dev.ednei.techFixApi.service;

import com.dev.ednei.techFixApi.infra.exceptions.errors.FirstAccessException;
import com.dev.ednei.techFixApi.model.User;
import com.dev.ednei.techFixApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    //metodos para controller
    public void checkForcePasswordAndUpdateUserDefault(User user, TokenService tokenService) {
        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder(10);

        if (user.getLogin().equals("UserAdmin") || bCryptPasswordEncoder.matches("TechFix@Api", user.getPassword())) {
            throw new FirstAccessException(
                    """
                            Identificamos que você fez login com o usuário padrão, mas ainda não alterou suas informações de acesso. Utilize o token enviado (válido por 10 minutos) e acesse a rota '/api/v2/employees/1' para atualizar seu username e dados pessoais. Em seguida, acesse '/api/v2/users/me/password' para atualizar sua senha.
                            """,  tokenService.tokenJwtForAlterPassword(user)
            );
        }

        if (!user.isForcePasswordChanger()) {
            throw new FirstAccessException("É necessário atualizar a senha no primeiro acesso. Use o token enviado com validade de 10 minutos e acesse a rota '/api/v2/users/me/password' para atualizar.", tokenService.tokenJwtForAlterPassword(user));
        }
    }
}
