package clashroyale.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordHasherTest {

    @Test
    void hash_then_verify_round_trips() {
        String hash = PasswordHasher.hash("hunter2");
        assertThat(PasswordHasher.verify("hunter2", hash)).isTrue();
        assertThat(PasswordHasher.verify("wrong",   hash)).isFalse();
    }

    @Test
    void hashes_are_unique_per_call() {
        // BCrypt salts are random — two hashes of the same password must differ.
        String a = PasswordHasher.hash("password");
        String b = PasswordHasher.hash("password");
        assertThat(a).isNotEqualTo(b);
        assertThat(PasswordHasher.verify("password", a)).isTrue();
        assertThat(PasswordHasher.verify("password", b)).isTrue();
    }

    @Test
    void legacy_plaintext_rows_still_authenticate_until_upgraded() {
        // Mirrors the path users take the first time they log in after the
        // password column has been migrated to BCrypt: the stored value is
        // still the original plaintext.
        assertThat(PasswordHasher.isBcrypt("hunter2")).isFalse();
        assertThat(PasswordHasher.verify("hunter2", "hunter2")).isTrue();
        assertThat(PasswordHasher.verify("wrong",   "hunter2")).isFalse();
    }

    @Test
    void null_inputs_are_rejected_or_safely_handled() {
        assertThatThrownBy(() -> PasswordHasher.hash(null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(PasswordHasher.verify(null, "anything")).isFalse();
        assertThat(PasswordHasher.verify("anything", null)).isFalse();
    }
}
