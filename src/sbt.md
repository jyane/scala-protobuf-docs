# sbtの設定のカスタマイズ

このページでは、基本的な使い方以外のsbtの設定について説明します。

## コンパイル対象のprotobufディレクトリを追加

以下のように `sourceDirectories in PB.protobufConfig` に追加します。[^src-dir-def]

```tut:invisible
import sbt._, Keys._
```

```tut:silent
import com.trueaccord.scalapb.{ScalaPbPlugin => PB}

sourceDirectories in PB.protobufConfig += file("新たにコンパイル対象に追加したいディレクトリへのpath")
```

[^src-dir-def]: 関連するsbt-protobufの定義場所 https://github.com/sbt/sbt-protobuf/blob/v0.3.3/src/main/scala/sbtprotobuf/ProtobufPlugin.scala#L21-L22

## Javaのclassとの相互変換

以下のオプションを設定することで、`toJavaProto`, `fromJavaProto` などの、Javaのclassとの相互変換のためのメソッドがScalaのコンパニオンobjectに追加されます。
デフォルトはoffです。
もし何らかの理由でJavaのclassとの相互変換が必要になった場合は設定してください。

```tut:silent
import com.trueaccord.scalapb.{ScalaPbPlugin => PB}

PB.javaConversions in PB.protobufConfig := true
```
