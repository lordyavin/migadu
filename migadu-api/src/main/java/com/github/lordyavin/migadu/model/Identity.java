package com.github.lordyavin.migadu.model;

public record Identity(
    String local_part,
    String domain_name,
    String address,
    String name,
    boolean may_send,
    boolean may_receive,
    boolean may_access_imap,
    boolean may_access_pop3,
    boolean may_access_managesieve,
    String password_use,
    String footer_active,
    String footer_plain_body,
    String footer_html_body) {}
