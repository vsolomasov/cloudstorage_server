package ru.donstu.cloudstorage.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.donstu.cloudstorage.domain.account.entity.Account;
import ru.donstu.cloudstorage.domain.account.enums.Role;
import ru.donstu.cloudstorage.exception.AesException;
import ru.donstu.cloudstorage.service.account.AccountService;
import ru.donstu.cloudstorage.service.userdetails.CustomUserDetailsService;
import ru.donstu.cloudstorage.service.userfiles.UserFilesService;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Реализация интерфейса {@link SecurityService}
 *
 * @author v.solomasov
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String ENCRYPTION_TYPE = "AES";

    private static final String RNG_ALGORITHM = "SHA1PRNG";

    private static final Integer KEY_LENGTH = 128;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserFilesService userFilesService;

    @Override
    public boolean isLoggedUser() {
        return getAuthentication().getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(Role.ROLE_USER.name()));
    }

    @Override
    public Account getLoggedAccount() {
        Object userDetails = getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            return accountService.findAccountByName(((UserDetails) userDetails).getUsername());
        }
        return null;
    }

    @Override
    public void autoLogin(String name, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    @Override
    public byte[] encryption(byte[] bytes) {
        try {
            return encrypt(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new AesException();
        } catch (NoSuchPaddingException e) {
            throw new AesException();
        } catch (InvalidKeyException e) {
            throw new AesException();
        } catch (BadPaddingException e) {
            throw new AesException();
        } catch (IllegalBlockSizeException e) {
            throw new AesException();
        }
    }

    @Override
    public byte[] decryption(byte[] bytes, String password) {
        try {
            return decrypt(bytes, password);
        } catch (NoSuchAlgorithmException e) {
            throw new AesException();
        } catch (NoSuchPaddingException e) {
            throw new AesException();
        } catch (InvalidKeyException e) {
            throw new AesException();
        } catch (BadPaddingException e) {
            throw new AesException();
        } catch (IllegalBlockSizeException e) {
            throw new AesException();
        }
    }

    /**
     * Шифрование последовательности байт алгоритмом AES
     *
     * @param sequenceBytes
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private byte[] encrypt(byte[] sequenceBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(getLoggedAccount().getPassword()), ENCRYPTION_TYPE);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(sequenceBytes);
        return encrypted;
    }

    /**
     * Дешифрование последовательности байт алгоритмом AES
     *
     * @param file
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private byte[] decrypt(byte[] file, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey(password), ENCRYPTION_TYPE);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(file);
        return decrypted;
    }

    /**
     * Генерация ключа
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private byte[] getRawKey(String password) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_TYPE);
        SecureRandom secureRandom = SecureRandom.getInstance(RNG_ALGORITHM);
        secureRandom.setSeed(password.getBytes());
        keyGenerator.init(KEY_LENGTH, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
