
## データ生成

> python3 -m pip install -r ./data_generation_script/requirements.txt
> python3 ./data_generation_script/generate.py

## スペック

CPU:apple silicon m1 pro
mem:32g

## 100万行

| クラス名 | 時間 | 備考 |
| - | - | - |
| CalculateAverage | 259ms | HashMap、BigDecimal使用 |
| CalculateAverage2 | 236ms | BigDecimal->int |

