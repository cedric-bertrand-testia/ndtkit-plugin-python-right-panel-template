# PythonPlugin

This plugin allows to interact with the [NDTkit](https://www.testia.com/product/ndtkit-ut/) desktop application.

## 📋 Prerequisites

1. **Python 3.9+** installed.
2. The **NDTkit 4.1+** installed.

## How to build

1. Create and activate a virtual environment:

```
python -m venv venv
./venv/Scripts/activate
```

2. Install requirements:

```
pip install -r requirements.txt
```

## How to launch

Just press F5 on Visual Studio Code or just run the main.py file:

```
python main.py
```

It will launch a local server on the configured port and NDTkit will be able to restitute it within its user interface with a web view.    