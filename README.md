# ファイルの内容と実行環境
  - Drawing_Strata_v3.java
    - 定性表現から地層モデル(断面図)を描画するプログラム
  - 褶曲地層テストデータ.txt
    - 褶曲地層を表す記号列が入力されたファイル
  - 不整合地層地層テストデータ.txt
    - 不整合地層を表す記号列が入力されたファイル
  - readme.pdf
    - このreadmeと同じ内容のファイル
  - 実行環境
    - OS: Windows 11 Home, 23H2
    - Eclipce: 4.27.0
    - Java: 17

# 操作方法
  1. Eclipseを起動し、ファイルを選択、実行
  2. 褶曲地層を表示する場合は「地層モデル描画」タブ  
     不整合地層を表示する場合は「不整合地層モデル描画」タブ  
     を選択  
     ![image](https://github.com/morita0413/ktakalab/assets/165443540/76f93736-4a1e-438e-8772-a577148e42c4)

# 操作方法(「地層モデル描画」タブ)
  1. 左側のテキストボックスに記号列(後述)を入力
  1. 右側のテキストボックスに曲がる方向(後述)を入力
  1. 描画ボタンを押す
  1. 回転や反転ボタンを押すと表示した地層が変化
     
  ![image](https://github.com/morita0413/ktakalab/assets/165443540/6dbef709-0c77-471a-99be-01c023f2b3a5)

# 操作方法(「不整合地層モデル描画」タブ)
  1. 左側のテキストボックスに記号列(後述)を入力
  1. 右側のテキストボックスに堆積順(後述)を入力
  1. 描画ボタンを押す

  ![image](https://github.com/morita0413/ktakalab/assets/165443540/26262995-98d5-41b7-8805-45a6e3002018)

# 表現方法について(褶曲地層)
  - 1から時計回りに辿る
  - まず1から始め、層が変化した時、**変化後の層**を表す記号を記号列に加え、頂点に出会えばその番号を加える
  - 下の例の場合「1A2B3C4B」が記号列となる

  ![image](https://github.com/morita0413/ktakalab/assets/165443540/c8de8dc3-4f17-4d66-85e9-01e430da7af2)

# 表現方法について(曲がる方向)
  - 褶曲地層における曲がる方向を1,2,3,4の4種類に分類する
  - 本来は矢印を使っているがプログラム用に1,2,3,4に当てはめている

  ![image](https://github.com/morita0413/ktakalab/assets/165443540/2734ef80-d6d1-45f3-8fa4-6d53f74880b2)

# 表現方法について(不整合地層)
  - 褶曲地層と基本的には同じだが、外周を1度辿るだけでは正確な地層が分からない場合があるので、一番上にある層を取り除き、再び外周を辿る(複雑な場合はこの処理を繰り返す)
  - 下の例の場合「12BCD34A->1CB2CD34」が記号列となる

  ![image](https://github.com/morita0413/ktakalab/assets/165443540/612b6f9c-69ea-4a76-8173-92aa19557613)
  
# 表現方法について(堆積順)
  - 堆積順は地層がどの順番で堆積したかを表す記号列
  - 下の例の場合「ABCD」が堆積順となる

  ![image](https://github.com/morita0413/ktakalab/assets/165443540/388b5e01-7870-45c3-b344-d4b6efd779e5)

# 実行例
  ![image](https://github.com/morita0413/ktakalab/assets/165443540/13dd711f-3c72-4d0c-b817-bf91e2857233)





     
