
## データ生成

> python3 -m pip install -r ./data_generation_script/requirements.txt
> python3 ./data_generation_script/generate.py

## スペック

CPU:apple silicon m1 pro
mem:32g

## 1_000_000 件

| クラス名 | 時間 | 備考 |
| - | - | - |
| CalculateAverage | 259ms | HashMap、BigDecimal使用 |
| CalculateAverage2 | 236ms | BigDecimal->int |
| CalculateAverage3 | 196ms | 100_000件ずつ Executors.newFixedThreadPool(4) ArrayList<> |
| CalculateAverage4 | 212ms | 100_000件ずつ Executors.newFixedThreadPool(4) new String[]() |

