package com.example.demo.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"), // ユニーク制約の列を設定
        @UniqueConstraint(columnNames = "email") }) // ユニーク制約の列を設定
@NoArgsConstructor // デフォルトコンストラクタを生成
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY) // データが最初にアクセスされるときに，LAZYにフェッチされる
    @JoinTable(name = "user_roles", // UserクラスとRoleクラスを多対多結合
            joinColumns = @JoinColumn(name = "user_id"), // 結合列にUserオブジェクトのidフィールドを指定
            inverseJoinColumns = @JoinColumn(name = "role_id")) // 結合列にRoleオブジェクトのidフィールドを指定
    private Set roles = new HashSet<>();

    // constructor
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
