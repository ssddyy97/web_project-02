
概要

本プロジェクトは、Spring Bootを利用して開発したWebアプリケーションです。
ユーザー管理や掲示板機能を中心としたWebサービスを実装し、Spring MVCアーキテクチャをベースにバックエンド開発の流れを学習することを目的としています。

Spring Securityによる認証・認可、JPAおよびMyBatisを利用したデータベース連携などを実装し、実際のWebサービスに近い構成で開発を行いました。

技術スタック
Backend

Java

Spring Boot

Spring MVC

Spring Security

JWT Authentication

Database

MySQL

Spring Data JPA

MyBatis

View

JSP

JSTL

その他

Spring AOP

Spring Cache

Gradle

主な機能

本アプリケーションでは以下の機能を実装しています。

ユーザー管理

ユーザー登録

ログイン

認証 / 認可（Spring Security）

掲示板機能

投稿作成

投稿一覧表示

投稿詳細表示

投稿編集

投稿削除

システムアーキテクチャ

本プロジェクトは、Spring MVCのレイヤードアーキテクチャを採用しています。

Client (Browser)
        ↓
Controller
        ↓
Service
        ↓
Repository
        ↓
Database

各レイヤーを分離することで、保守性と拡張性を向上させています。

プロジェクト構造

src
 ├ main
 │   ├ java
 │   │   └ controller
 │   │   └ service
 │   │   └ repository
 │   │   └ domain
 │   │   └ dto
 │   │   └ config
 │   │   └ aop
 │   │
 │   ├ resources
 │   │   └ application.properties
 │   │   └ mappers
 │   │
 │   └ webapp
 │       └ WEB-INF
 │           └ views (JSP)
 
学習目的

本プロジェクトでは以下の内容を学習しました。

Spring Bootを利用したWebアプリケーション開発

Spring MVCアーキテクチャの理解

Spring Securityによる認証処理

JPA / MyBatisによるデータベース連携

レイヤードアーキテクチャ設計

今後の改善予定

今後は以下の機能を追加する予定です。

REST API対応

フロントエンドフレームワーク（React / Vue）連携

Docker / Kubernetesによるデプロイ

CI/CDパイプライン構築
