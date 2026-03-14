#ifndef NONGMOL_FUTURE_H
#define NONGMOL_FUTURE_H

// ช่องทางสำหรับเชื่อมต่อโมเดลตัวใหม่ๆ ในอนาคต (เช่น Multi-modal)
namespace AgentFuture {
    class ExtraEngine {
        virtual void processExternalTask() = 0;
    };
}

#endif
